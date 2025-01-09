plugins {
	java
	id("org.springframework.boot") version "3.4.1"
	id("io.spring.dependency-management") version "1.1.7"
	id("org.openapi.generator") version "7.0.1"
}

group = "org.meetmybar"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("jakarta.servlet:jakarta.servlet-api:6.0.0")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")
	implementation("org.openapitools:jackson-databind-nullable:0.2.6")
	implementation("io.swagger.core.v3:swagger-annotations:2.2.15")
	implementation("org.mariadb.jdbc:mariadb-java-client:2.1.2")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

val spec = File(projectDir, "src/main/resources/swagger/meetMyBarSwagger.yaml")
	.absolutePath
	.replace("\\", "/")

openApiGenerate {
	validateSpec.set(false)  // Désactive la validation du spec
	generatorName.set("spring")
	inputSpec.set(spec)
	outputDir.set("${layout.buildDirectory.asFile.get()}/generated-sources")
	apiPackage.set("org.meetmybar.api.controller")
	modelPackage.set("org.meetmybar.api.model")
	configOptions.set(mapOf(
		"dateLibrary" to "java8",
		"interfaceOnly" to "true",
		"useSpringBoot3" to "true",
		"useSpringController" to "true",
		"useTags" to "true",
		"documentationProvider" to "springdoc",
		"useJakartaEe" to "true"
	))
}

sourceSets {
	main {
		java {
			srcDirs("${layout.buildDirectory.asFile.get()}/generated-sources/src/main/java")
		}
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.named("compileJava") {
	dependsOn("openApiGenerate")
}