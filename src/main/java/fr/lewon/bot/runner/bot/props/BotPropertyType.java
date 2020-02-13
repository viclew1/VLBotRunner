package fr.lewon.bot.runner.bot.props;

public enum BotPropertyType {

    INTEGER(Integer.class, Integer::parseInt),
    STRING(String.class, v -> v),
    FLOAT(Float.class, Float::parseFloat),
    BOOLEAN(Boolean.class, Boolean::parseBoolean);

    private Class<?> refClass;
    private TypeChecker typeChecker;

    BotPropertyType(Class<?> refClass, TypeChecker typeChecker) {
        this.refClass = refClass;
        this.typeChecker = typeChecker;
    }

    public Object parse(String value) throws Exception {
        return this.typeChecker.castValue(value);
    }

}

interface TypeChecker {

    Object castValue(String value) throws Exception;

}