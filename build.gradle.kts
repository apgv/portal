import org.flywaydb.gradle.FlywayExtension
import org.gradle.jvm.tasks.Jar
import org.jooq.util.GenerationTool
import org.jooq.util.jaxb.Configuration
import org.jooq.util.jaxb.Database
import org.jooq.util.jaxb.Generator
import org.jooq.util.jaxb.Jdbc
import org.jooq.util.jaxb.Target

plugins {
    id("org.jetbrains.kotlin.jvm") version ("1.1.3-2")
    id("org.flywaydb.flyway") version ("4.2.0")
}

buildscript {
    repositories {
        mavenCentral()
    }

    dependencies {
        classpath("com.h2database:h2:1.4.196")
        classpath("org.jooq:jooq:3.9.3")
        classpath("org.jooq:jooq-meta:3.9.3")
        classpath("org.jooq:jooq-codegen:3.9.3")
    }
}

configure<FlywayExtension> {
    url = "jdbc:h2:./jooq_db"
    user = "sa"
    password = ""
    schemas = arrayOf("public")
}

group = "no.skotbuvel"
version = "1.0-SNAPSHOT"

tasks {
    val buildFrontEnd by creating(Exec::class) {
        workingDir("frontend")
        commandLine("npm", "run", "build")
    }

    val copyFrontEnd by creating(Copy::class) {
        from("frontend/build")
        into("build/resources/main/frontend")
    }
    copyFrontEnd.dependsOn(buildFrontEnd)

    val jooqCodeGen by creating(DefaultTask::class) {
        val configuration = Configuration()
                .withJdbc(Jdbc()
                        .withDriver("org.h2.Driver")
                        .withUrl("jdbc:h2:./jooq_db")
                        .withUser("sa")
                        .withPassword(""))
                .withGenerator(Generator()
                        .withDatabase(Database()
                                .withName("org.jooq.util.h2.H2Database")
                                .withIncludes(".*")
                                .withExcludes("")
                                .withInputSchema("public"))
                        .withTarget(Target()
                                .withPackageName("no.skotbuvel.portal.jooq.generated")
                                .withDirectory("build/classes/java/main")))

        GenerationTool.generate(configuration)
    }
    jooqCodeGen.dependsOn(tasks["flywayMigrate"])

    val fatJar by creating(Jar::class) {
        manifest {
            attributes["Implementation-Title"] = project.name
            attributes["Implementation-Version"] = version
            attributes["Main-Class"] = "no.skotbuvel.portal.ApplicationKt"
        }
        baseName = project.name + "-all"
        from(configurations.compile.copy().map { if (it.isDirectory) it else zipTree(it) })
        with(tasks["jar"] as CopySpec)
    }
    fatJar.dependsOn(copyFrontEnd, jooqCodeGen)
}

repositories {
    mavenCentral()
}

dependencies {
    compile("org.jetbrains.kotlin:kotlin-stdlib-jre8:1.1.3-2")
    compile("com.sparkjava:spark-core:2.6.0")
    compile("org.slf4j:slf4j-simple:1.7.25")
    compile("com.google.code.gson:gson:2.8.1")
}