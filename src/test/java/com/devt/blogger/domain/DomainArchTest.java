package com.devt.blogger.domain;

import com.devt.blogger.domain.services.DomainService;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.core.importer.ImportOption.DoNotIncludeTests;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@AnalyzeClasses(
        packages = "com.devt.blogger",
        importOptions = DoNotIncludeTests.class
)
class DomainArchTest {

    @ArchTest
    static final ArchRule shouldNotDependsOnExternalClasses = classes()
            .that()
            .resideInAPackage("..domain..")
            .should()
            .onlyDependOnClassesThat()
            .resideInAnyPackage(
                    "com.devt.blogger.domain..",
                    "java..");

    @ArchTest
    static final ArchRule inboundShouldOnlyBeInterfaces = classes()
            .that()
            .resideInAPackage("..domain.inbound..")
            .should()
            .beInterfaces();

    @ArchTest
    static final ArchRule outboundShouldOnlyBeInterfaces = classes()
            .that()
            .resideInAPackage("..domain.outbound..")
            .should()
            .beInterfaces();

    @ArchTest
    static final ArchRule servicesShouldBeAnnotated = classes()
            .that()
            .resideInAPackage("..domain.services..")
            .and()
            .doNotBelongToAnyOf(DomainService.class)
            .should()
            .beAnnotatedWith(DomainService.class)
            .andShould()
            .haveSimpleNameEndingWith("Service");

    @ArchTest
    static final ArchRule exceptionsShouldHaveNameEndingWithException = classes()
            .that()
            .resideInAPackage("..domain.exceptions..")
            .should()
            .beAssignableTo(Exception.class)
            .andShould()
            .haveSimpleNameEndingWith("Exception");

}
