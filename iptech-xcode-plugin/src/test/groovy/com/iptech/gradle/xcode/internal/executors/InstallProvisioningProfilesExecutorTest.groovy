package com.iptech.gradle.xcode.internal.executors

import spock.lang.Specification

class InstallProvisioningProfilesExecutorTest extends Specification {
    def "getUUIDMethod returns UUID of provisioning profile"() {
        when:
        File provisioningProfile = getResourceAsFile('/provisioning-profile.mobileprovision')
        String uuid = InstallProvisioningProfilesExecutor.getUUID(provisioningProfile)

        then:
        uuid == '7a8310e3-1a09-44ae-a22b-d18096c0a31b'
    }

    private File getResourceAsFile(String resourceName) {
        URL url = getClass().getResource(resourceName)
        String file = url.getFile().replaceAll('%252F', '%2F')
        return new File(file)
    }
}
