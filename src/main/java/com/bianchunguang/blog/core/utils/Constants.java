package com.bianchunguang.blog.core.utils;

public class Constants {

    public enum SystemConfigConstants {

        UI_LOCAL("ui.local"),
        UI_THEME("ui.theme"),
        SPRING_MESSAGE_BASENAME("spring.messages.basename");

        private String propertyName;
        private boolean require = true;
        private String defaultValue = "";

        SystemConfigConstants(String propertyName) {
            this.propertyName = propertyName;
        }

        public boolean isRequire() {
            return require;
        }

        public String getDefaultValue() {
            return defaultValue;
        }

        @Override
        public String toString() {
            return propertyName;
        }

    }

}

