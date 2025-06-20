package com.devt.blogger.infrastructure.configuration;

import com.devt.blogger.BloggerApplication;
import com.devt.blogger.infrastructure.outbound.OutboundAdapter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        basePackageClasses = BloggerApplication.class,
        includeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {OutboundAdapter.class})})
public class OutboundAdapterConfiguration {
}
