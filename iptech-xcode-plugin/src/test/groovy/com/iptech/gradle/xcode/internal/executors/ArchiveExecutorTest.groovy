package com.iptech.gradle.xcode.internal.executors

import com.iptech.gradle.xcode.api.ArchiveSpec
import com.iptech.gradle.xcode.api.XcodeBuildSpec
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.internal.model.DefaultObjectFactory
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import org.gradle.invocation.DefaultGradle
import org.gradle.testkit.runner.GradleRunner
import spock.lang.Specification

import javax.inject.Inject
import org.gradle.testkit.runner.internal.GradleProvider;
class ArchiveExecutorTest extends Specification {
    XcodeBuildExecutor mockXcodeBuildExecutor = Mock()
    ObjectFactory mockObjectFactory = GradleRunner.newInstance().build()
    XcodeBuildSpec mockXcodeBuildSpec = Mock()

    def "test"() {
        given:
        //mockObjectFactory.newInstance(XcodeBuildSpec.class) >> mockXcodeBuildSpec
        ArchiveExecutor archiveExecutor = new ArchiveExecutor(mockXcodeBuildExecutor, mockObjectFactory)

        when:
        archiveExecutor.exec(EXPECTED_SPEC)

        then:
        1 * mockXcodeBuildExecutor.exec(_)
    }

    static ArchiveSpec EXPECTED_SPEC = new ArchiveSpec() {
        @Override
        Property<String> getConfiguration() {
            return null
        }

        @Override
        Property<String> getScheme() {
            return null
        }

        @Override
        DirectoryProperty getDerivedDataPath() {
            return null
        }

        @Override
        DirectoryProperty getArchivePath() {
            return null
        }

        @Override
        Property<String> getCODE_SIGN_IDENTITY() {
            return null
        }

        @Override
        Property<String> getCODE_SIGN_STYLE() {
            return null
        }

        @Override
        Property<String> getDEVELOPMENT_TEAM() {
            return null
        }

        @Override
        Property<String> getPROVISIONING_PROFILE_SPECIFIER() {
            return null
        }

        @Override
        Property<String> getCODE_SIGNING_REQUIRED() {
            return null
        }

        @Override
        Property<String> getCODE_SIGNING_ALLOWED() {
            return null
        }

        @Override
        ListProperty<String> getAdditionalArguments() {
            return null
        }

        @Override
        DirectoryProperty getProjectPath() {
            return null
        }
    }
}
