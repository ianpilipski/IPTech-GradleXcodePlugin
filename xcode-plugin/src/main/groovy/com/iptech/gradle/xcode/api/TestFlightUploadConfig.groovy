package com.iptech.gradle.xcode.api

import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input

import javax.inject.Inject

abstract class TestFlightUploadConfig implements TestFlightSpec {
    final String name

    @Inject
    TestFlightUploadConfig(String name) {
        this.name = name
    }
}