package com.iptech.gradle.xcode.tasks

import com.iptech.gradle.xcode.api.ArchiveSpec
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.TaskAction
import org.gradle.process.ExecResult

abstract class Archive extends DefaultTask implements ArchiveSpec {
    @TaskAction
    ExecResult execute() {
        return project.xcode.archive(this)
    }
}
