
package org.mybatis.generator.api;

import static org.mybatis.generator.internal.util.messages.Messages.getString;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.mybatis.generator.logging.LogFactory;

/**
 * This class allows the code generator to be run from the command line.
 */
public class ShellRunner {
    /**
     * -config：配置文件，也是我们自动代码对应的xml文件，从后面的执行逻辑可以看出，这个文件是必须有的。
     * 它包含了jdbc的类路径驱动包、jdbc相关连接参数信息、接口相关参数信息、领域对象相关参数信息以及要生成代码的表信息等等。
    */
    private static final String CONFIG_FILE = "-configfile"; //$NON-NLS-1$

    /**
     * -overwrite：默认下载时带此参数，表示如果之前已经生成过代码，但是还是要用新的进行覆盖；可以忽略此参数。
    */
    private static final String OVERWRITE = "-overwrite"; //$NON-NLS-1$
    /* -contextids：上下文表示，用逗号分隔；可以忽略。 */
    private static final String CONTEXT_IDS = "-contextids"; //$NON-NLS-1$
    /* -table：从命令行传入生成的表，用逗号分隔；可以忽略 */
    private static final String TABLES = "-tables"; //$NON-NLS-1$
    /* -verbose：控制台是否输出详细过程日志；可忽略。 */
    private static final String VERBOSE = "-verbose"; //$NON-NLS-1$
    /* -forceJavaLogging：强制java日志；可忽略。 */
    private static final String FORCE_JAVA_LOGGING = "-forceJavaLogging"; //$NON-NLS-1$
    /* -？：显示帮助信息，将忽略其他信息，不执行代码生成。 */
    private static final String HELP_1 = "-?"; //$NON-NLS-1$
    private static final String HELP_2 = "-h"; //$NON-NLS-1$

    public static void main(String[] args) {
        if (args.length == 0) {
            usage();
            System.exit(0);
            return; // only to satisfy compiler, never returns
        }

        Map<String, String> arguments = parseCommandLine(args);
        // 仅仅是帮助信息，有该参数时不生成代码，仅仅显示各个命令行参数的作用
        if (arguments.containsKey(HELP_1)) {
            usage();
            System.exit(0);
            return; // only to satisfy compiler, never returns
        }
        // 必须要有配置文件
        if (!arguments.containsKey(CONFIG_FILE)) {
            writeLine(getString("RuntimeError.0")); //$NON-NLS-1$
            return;
        }

        List<String> warnings = new ArrayList<>();

        String configfile = arguments.get(CONFIG_FILE);
        File configurationFile = new File(configfile);
        if (!configurationFile.exists()) {
            writeLine(getString("RuntimeError.1", configfile)); //$NON-NLS-1$
            return;
        }
        // table参数，以,分割，从命令读取要生成代码的表
        Set<String> fullyqualifiedTables = new HashSet<>();
        if (arguments.containsKey(TABLES)) {
            StringTokenizer st = new StringTokenizer(arguments.get(TABLES), ","); //$NON-NLS-1$
            while (st.hasMoreTokens()) {
                String s = st.nextToken().trim();
                if (s.length() > 0) {
                    fullyqualifiedTables.add(s);
                }
            }
        }
        // contextids参数，以,分割，从命令行读取上下数据
        Set<String> contexts = new HashSet<>();
        if (arguments.containsKey(CONTEXT_IDS)) {
            StringTokenizer st = new StringTokenizer(
                    arguments.get(CONTEXT_IDS), ","); //$NON-NLS-1$
            while (st.hasMoreTokens()) {
                String s = st.nextToken().trim();
                if (s.length() > 0) {
                    contexts.add(s);
                }
            }
        }

        try {
            ConfigurationParser cp = new ConfigurationParser(warnings);
            // 将xml配置文件读入到配置对象中
            Configuration config = cp.parseConfiguration(configurationFile);
            // 缺省的shell回调对象
            DefaultShellCallback shellCallback = new DefaultShellCallback( arguments.containsKey(OVERWRITE));
            // 实例化生成对象
            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, shellCallback, warnings);
            // 实例化处理过程回调函数，如verbose参数未提供，则不为null
            ProgressCallback progressCallback = arguments.containsKey(VERBOSE) ? new VerboseProgressCallback() : null;

            // 生成文件
            myBatisGenerator.generate(progressCallback, contexts, fullyqualifiedTables);

        } catch (XMLParserException e) {
            writeLine(getString("Progress.3")); //$NON-NLS-1$
            writeLine();
            for (String error : e.getErrors()) {
                writeLine(error);
            }

            return;
        } catch (SQLException | IOException e) {
            e.printStackTrace(System.out);
            return;
        } catch (InvalidConfigurationException e) {
            writeLine(getString("Progress.16")); //$NON-NLS-1$
            for (String error : e.getErrors()) {
                writeLine(error);
            }
            return;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        for (String warning : warnings) {
            writeLine(warning);
        }

        if (warnings.isEmpty()) {
            writeLine(getString("Progress.4")); //$NON-NLS-1$
        } else {
            writeLine();
            writeLine(getString("Progress.5")); //$NON-NLS-1$
        }
    }

    private static void usage() {
        writeLine(getString("Usage")); //$NON-NLS-1$
    }

    private static void writeLine(String message) {
        System.out.println(message);
    }

    private static void writeLine() {
        System.out.println();
    }

    private static Map<String, String> parseCommandLine(String[] args) {
        List<String> errors = new ArrayList<>();
        Map<String, String> arguments = new HashMap<>();

        for (int i = 0; i < args.length; i++) {
            if (CONFIG_FILE.equalsIgnoreCase(args[i])) {
                if ((i + 1) < args.length) {
                    arguments.put(CONFIG_FILE, args[i + 1]);
                } else {
                    errors.add(getString("RuntimeError.19", CONFIG_FILE)); //$NON-NLS-1$
                }
                i++;
            } else if (OVERWRITE.equalsIgnoreCase(args[i])) {
                arguments.put(OVERWRITE, "Y"); //$NON-NLS-1$
            } else if (VERBOSE.equalsIgnoreCase(args[i])) {
                arguments.put(VERBOSE, "Y"); //$NON-NLS-1$
            } else if (HELP_1.equalsIgnoreCase(args[i])) {
                arguments.put(HELP_1, "Y"); //$NON-NLS-1$
            } else if (HELP_2.equalsIgnoreCase(args[i])) {
                // put HELP_1 in the map here too - so we only
                // have to check for one entry in the mainline
                arguments.put(HELP_1, "Y"); //$NON-NLS-1$
            } else if (FORCE_JAVA_LOGGING.equalsIgnoreCase(args[i])) {
                LogFactory.forceJavaLogging();
            } else if (CONTEXT_IDS.equalsIgnoreCase(args[i])) {
                if ((i + 1) < args.length) {
                    arguments.put(CONTEXT_IDS, args[i + 1]);
                } else {
                    errors.add(getString(
                            "RuntimeError.19", CONTEXT_IDS)); //$NON-NLS-1$
                }
                i++;
            } else if (TABLES.equalsIgnoreCase(args[i])) {
                if ((i + 1) < args.length) {
                    arguments.put(TABLES, args[i + 1]);
                } else {
                    errors.add(getString("RuntimeError.19", TABLES)); //$NON-NLS-1$
                }
                i++;
            } else {
                errors.add(getString("RuntimeError.20", args[i])); //$NON-NLS-1$
            }
        }

        if (!errors.isEmpty()) {
            for (String error : errors) {
                writeLine(error);
            }

            System.exit(-1);
        }

        return arguments;
    }
}
