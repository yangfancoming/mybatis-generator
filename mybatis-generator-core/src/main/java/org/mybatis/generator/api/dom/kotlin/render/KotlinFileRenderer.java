
package org.mybatis.generator.api.dom.kotlin.render;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.mybatis.generator.api.dom.kotlin.KotlinFile;
import org.mybatis.generator.api.dom.kotlin.KotlinNamedItem;

public class KotlinFileRenderer {

    public String render(KotlinFile kotlinFile) {
        List<String> lines = new ArrayList<>();
        KotlinNamedItemRenderer renderer = new KotlinNamedItemRenderer();

        kotlinFile.getPackage().ifPresent(p -> lines.add("package " + p)); //$NON-NLS-1$

        lines.addAll(prependBlankLineIfNotEmpty(lines.size(), renderImports(kotlinFile)));

        for (KotlinNamedItem item : kotlinFile.getNamedItems()) {
            lines.addAll(prependBlankLineIfNotEmpty(lines.size(), renderer.render(item)));
        }

        lines.addAll(0, kotlinFile.getFileCommentLines());
        return lines.stream()
                .collect(Collectors.joining(System.getProperty("line.separator"))); //$NON-NLS-1$
    }

    private List<String> prependBlankLineIfNotEmpty(int currentLength, List<String> in) {
        if (in.isEmpty() || currentLength == 0) {
            return in;
        }

        in.add(0, ""); //$NON-NLS-1$
        return in;
    }

    private List<String> renderImports(KotlinFile kotlinFile) {
        return kotlinFile.getImports().stream()
                .map(s -> "import " + s) //$NON-NLS-1$
                .collect(Collectors.toList());
    }
}
