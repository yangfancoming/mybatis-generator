
package org.mybatis.generator.api.dom.kotlin;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class KotlinFunction extends KotlinNamedItem {
    private List<KotlinArg> arguments = new ArrayList<>();
    private List<String> codeLines = new ArrayList<>();
    private String explicitReturnType;
    private boolean isOneLineFunction;

    private KotlinFunction(Builder builder) {
        super(builder);
        arguments.addAll(builder.arguments);
        codeLines.addAll(builder.codeLines);
        explicitReturnType = builder.explicitReturnType;
        isOneLineFunction = builder.isOneLineFunction;
    }

    public void addArgument(KotlinArg argument) {
        arguments.add(argument);
    }

    public List<KotlinArg> getArguments() {
        return arguments;
    }

    public void addCodeLine(String codeLine) {
        this.codeLines.add(codeLine);
    }

    public void addCodeLines(List<String> codeLines) {
        this.codeLines.addAll(codeLines);
    }

    public List<String> getCodeLines() {
        return codeLines;
    }

    public Optional<String> getExplicitReturnType() {
        return Optional.ofNullable(explicitReturnType);
    }

    public boolean isOneLineFunction() {
        return isOneLineFunction;
    }

    @Override
    public <R> R accept(KotlinNamedItemVisitor<R> visitor) {
        return visitor.visit(this);
    }

    public static Builder newOneLineFunction(String name) {
        return new Builder(name, true);
    }

    public static Builder newMultiLineFunction(String name) {
        return new Builder(name, false);
    }

    public static class Builder extends AbstractBuilder<Builder> {
        private boolean isOneLineFunction;
        private List<KotlinArg> arguments = new ArrayList<>();
        private List<String> codeLines = new ArrayList<>();
        private String explicitReturnType;

        private Builder(String name, boolean isOneLineFunction) {
            super(name);
            this.isOneLineFunction = isOneLineFunction;
        }

        public Builder withArgument(KotlinArg argument) {
            arguments.add(argument);
            return this;
        }

        public Builder withCodeLine(String codeLine) {
            codeLines.add(codeLine);
            return this;
        }

        public Builder withExplicitReturnType(String explicitReturnType) {
            this.explicitReturnType = explicitReturnType;
            return this;
        }

        public Builder getThis() {
            return this;
        }

        public KotlinFunction build() {
            return new KotlinFunction(this);
        }
    }
}
