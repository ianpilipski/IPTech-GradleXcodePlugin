package com.iptech.gradle.xcode.tasks

import com.iptech.gradle.xcode.api.XcodeBuildSpec
import com.iptech.gradle.xcode.api.XcodeProjectPathSpec
import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction

import javax.inject.Inject


abstract class Clean extends DefaultTask implements XcodeProjectPathSpec {
    private final ObjectFactory objectFactory

    @Input abstract Property<String> getScheme()
    @InputDirectory @Optional abstract DirectoryProperty getDerivedDataPath()

    @Inject
    Clean(ObjectFactory objectFactory) {
        this.objectFactory = objectFactory
    }

    @TaskAction
    protected void execute() {
        XcodeBuildSpec spec = objectFactory.newInstance(XcodeBuildSpec)
        spec.projectPath = projectPath
        spec.scheme = scheme
        spec.derivedDataPath = derivedDataPath
        spec.additionalArguments.add('clean')

        project.xcode.xcodeBuild(spec)
    }
}
