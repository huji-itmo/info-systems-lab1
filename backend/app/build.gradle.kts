plugins {
    id("war")
    kotlin("jvm") version "2.2.20"
    kotlin("plugin.allopen") version "2.2.20"
    kotlin("plugin.noarg") version "2.2.20"
    kotlin("plugin.jpa") version "2.2.20"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))

    // EclipseLink (JPA implementation)
    implementation("org.eclipse.persistence:eclipselink:4.0.8") // or latest

    // PostgreSQL JDBC driver
    implementation("org.postgresql:postgresql:42.7.3")

    // https://mvnrepository.com/artifact/jakarta.platform/jakarta.jakartaee-api
    compileOnly("jakarta.platform:jakarta.jakartaee-api:11.0.0")
    compileOnly("jakarta.ws.rs:jakarta.ws.rs-api:4.0.0")
}

testing {
    suites {
        // Configure the built-in test suite
        val test by getting(JvmTestSuite::class) {
            // Use Kotlin Test test framework
            useKotlinTest("1.9.22")
        }
    }
}

// Apply a specific Java toolchain to ease working on different environments.
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

kotlin {
    jvmToolchain(17)
}

allOpen {
    annotation("jakarta.enterprise.context.ApplicationScoped")
    annotation("jakarta.enterprise.context.RequestScoped")
    annotation("jakarta.ws.rs.Path")
}

noArg {
    annotation("jakarta.enterprise.context.RequestScoped")
    annotation("jakarta.enterprise.context.ApplicationScoped")
    annotation("org.example.JsonDeserializable")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
    }
}
