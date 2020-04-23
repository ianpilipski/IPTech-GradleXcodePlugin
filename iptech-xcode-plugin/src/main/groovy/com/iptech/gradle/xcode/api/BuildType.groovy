package com.iptech.gradle.xcode.api

import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input

import javax.inject.Inject

abstract class BuildType {
    final String name

    @Input abstract Property<String> getConfiguration()
    @Input abstract Property<String> getScheme()
    @Input abstract DirectoryProperty getArchivePath()

    @Input final NamedDomainObjectContainer<ExportArchiveType> exportArchives

    @Inject
    BuildType(String name, ObjectFactory objectFactory) {
        this.name = name
        this.exportArchives = objectFactory.domainObjectContainer(ExportArchiveType)
    }
}