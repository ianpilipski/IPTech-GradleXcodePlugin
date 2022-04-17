package com.iptech.gradle.xcode.tasks

import com.iptech.gradle.xcode.api.TestFlightSpec
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.process.ExecResult

abstract class TestFlightUpload extends DefaultTask implements TestFlightSpec {
    @TaskAction
    protected ExecResult execute() {
        return project.xcode.upload(this)
    }
}
