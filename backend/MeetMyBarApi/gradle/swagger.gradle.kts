plugins {
    java
    id("org.springframework.boot") version "3.4.1"
    id("io.spring.dependency-management") version "1.1.7"
    id("org.openapi.generator") version "7.0.1"
}

repositories {
    mavenCentral()
}

dependencies {
    "implementation"("org.springframework.boot:spring-boot-starter-data-jdbc")
    "implementation"("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")
    "implementation"("org.openapitools:jackson-databind-nullable:0.2.6")
    "implementation"("io.swagger.core.v3:swagger-annotations:2.2.15")
    "implementation"("org.mariadb.jdbc:mariadb-java-client:2.1.2")
    "testImplementation"("org.springframework.boot:spring-boot-starter-test")
    "testRuntimeOnly"("org.junit.platform:junit-platform-launcher")
}

openApiGenerate {
    generatorName.set("spring")
    inputSpec.set("${project.projectDir}/src/main/resources/swagger/meetMyBarSwagger.yaml")
    outputDir.set("${project.buildDir}/generated")
    apiPackage.set("org.meetmybar.api.controller")
    modelPackage.set("org.meetmybar.api.model")
    configOptions.set(mapOf(
        "dateLibrary" to "java8",
        "interfaceOnly" to "true",
        "useSpringBoot3" to "true",
        "documentationProvider" to "springdoc",
        "serializableModel" to "true"
    ))
}

sourceSets {
    main {
        java {
            srcDir("${project.buildDir}/generated/src/main/java")
        }
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.named("compileJava") {
    dependsOn("openApiGenerate")
}