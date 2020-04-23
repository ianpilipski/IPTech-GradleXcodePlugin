package com.iptech.gradle.xcode.api

abstract class ExportArchiveType implements ExportArchiveSpec {
    final String name

    ExportArchiveType(String name) {
        this.name = name
    }
}
