package com.spring.actor.lib.pipelines.api;

import com.spring.actor.lib.pipelines.annotations.Pipeline;
import com.spring.actor.lib.pipelines.annotations.ToMessage;
import com.spring.actor.lib.pipelines.message.IMessage;
import com.spring.actor.lib.pipelines.message.Message;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MessageResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {

        return parameter.getParameterType().isAssignableFrom(IMessage.class);
    }

    @Override
    public IMessage resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory
    ) throws Exception {
        IMessage message = null;
        String pipelineId = null;
        Map<String, Object> body = new HashMap<>();
        List<Annotation> annotations = Arrays.asList(parameter.getMethodAnnotations());
        if (!annotations.isEmpty()) {
            List<Annotation> pipelineAnnotations = annotations
                    .stream()
                    .filter(a -> a.annotationType().isAssignableFrom(Pipeline.class))
                    .collect(Collectors.toList());
            if (pipelineAnnotations.size() > 0) {
                pipelineId = ((Pipeline) pipelineAnnotations.get(0)).value();
            }
        }
        Arrays.asList(parameter.getMethod().getParameters())
                .stream()
                .filter(p -> p.getAnnotationsByType(ToMessage.class).length > 0)
                .forEach(
                        p -> {
                            String key = p.getAnnotation(ToMessage.class).value();
                            body.put(key, webRequest.getParameterValues(p.getName()));
                        }
                );


        if (null != pipelineId) {
            message = new Message(pipelineId, body);
        }

        return message;
    }
}
