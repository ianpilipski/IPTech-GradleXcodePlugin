package com.iptech.gradle.xcode.tasks

import com.iptech.gradle.xcode.api.ArchiveSpec
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.TaskAction
import org.gradle.process.ExecResult

abstract class Archive extends DefaultTask implements ArchiveSpec {

    //TODO: split out into build/archive so I don't need this Extra Property?
    @Input @Optional
    protected String getDerivedDataPath_() {
        if(derivedDataPath.isPresent()) return derivedDataPath.get().asFile.absolutePath
        return null
    }

    @TaskAction
    ExecResult execute() {
        return project.xcode.archive(this)
    }
}
