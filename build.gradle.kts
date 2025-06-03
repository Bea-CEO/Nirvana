plugins {
    id("com.possible-triangle.gradle") version ("0.2.11")
}

subprojects {
    repositories {
        mavenLocal()
        modrinthMaven()

        maven {
            url = uri("https://mvn.devos.one/snapshots/")
            content {
                includeGroup("com.tterrag.registrate_fabric")
                includeGroup("io.github.fabricators_of_create.Porting-Lib")
            }
        }

        maven {
            url = uri("https://maven.tterrag.com/")
            content {
                includeGroup("com.tterrag.registrate")
            }
        }

        maven {
            url = uri("https://maven.createmod.net")
            content {
                includeGroup("com.simibubi.create")
                includeGroup("net.createmod.ponder")
                includeGroup("dev.engine-room.flywheel")
            }
        }

        maven {
            url = uri("https://maven.blamejared.com/")
            content {
                includeGroup("mezz.jei")
            }
        }

        maven {
            url = uri("https://jitpack.io")
            content {
                includeGroup("com.github.llamalad7.mixinextras")
            }
        }

        maven {
            url = uri("https://registry.somethingcatchy.net/repository/maven-releases/")
            content {
                includeGroup("dev.galena")
            }
        }
    }

    tasks.withType<Jar> {
        exclude("**/*.bbmodel")
    }
}

enableSonarQube()
enableSpotless()
