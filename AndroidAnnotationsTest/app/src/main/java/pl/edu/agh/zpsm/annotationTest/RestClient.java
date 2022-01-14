package pl.edu.agh.zpsm.annotationTest;

import org.androidannotations.rest.spring.annotations.Get;
import org.androidannotations.rest.spring.annotations.Path;
import org.androidannotations.rest.spring.annotations.Rest;
import org.springframework.http.converter.StringHttpMessageConverter;

@Rest(rootUrl = "https://gorest.co.in", converters = {StringHttpMessageConverter.class})
public interface RestClient {

    @Get("/public/v1/posts/{searchString}")
    String getResult(@Path String searchString);
}