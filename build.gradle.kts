plugins {
    java
    application
}

group = "de.eazypaulcode"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

application {
    mainClass.set("de.eazypaulcode.todobot.Main")
}

dependencies {
    implementation("net.dv8tion:JDA:5.0.0-alpha.9")
    implementation("org.fusesource.jansi:jansi:2.4.0")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}