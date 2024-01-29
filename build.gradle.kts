import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.2.1"
    id("io.spring.dependency-management") version "1.1.4"
    kotlin("jvm") version "1.9.21"
    kotlin("plugin.spring") version "1.9.21"
}

group = "org.lanier"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {
    // https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter
    implementation("org.springframework.boot:spring-boot-starter:3.2.1")

    implementation("org.jetbrains.kotlin:kotlin-reflect")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    // https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-web
    implementation("org.springframework.boot:spring-boot-starter-web:3.2.1")

    // https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-websocket
    implementation("org.springframework.boot:spring-boot-starter-websocket:3.2.0")

    // https://mvnrepository.com/artifact/org.mybatis.spring.boot/mybatis-spring-boot-starter
    implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:3.0.3")

    // https://mvnrepository.com/artifact/org.mybatis/mybatis
    implementation("org.mybatis:mybatis:3.5.12")

    // https://mvnrepository.com/artifact/org.mybatis/mybatis-spring
    implementation("org.mybatis:mybatis-spring:3.0.3")

    // https://mvnrepository.com/artifact/com.mysql/mysql-connector-j
    implementation("com.mysql:mysql-connector-j:8.3.0")

    // https://mvnrepository.com/artifact/org.springframework/spring-jdbc
    implementation("org.springframework:spring-jdbc:6.1.3")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
