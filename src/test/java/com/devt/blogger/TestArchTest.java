package com.devt.blogger;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@AnalyzeClasses(
        packages = "com.devt.blogger",
        importOptions = ImportOption.OnlyIncludeTests.class
)
class TestArchTest {

    @ArchTest
    static final ArchRule testsShouldNotUseMockito = noClasses()
            .should()
            .dependOnClassesThat()
            .resideInAPackage("org.mockito..");
}