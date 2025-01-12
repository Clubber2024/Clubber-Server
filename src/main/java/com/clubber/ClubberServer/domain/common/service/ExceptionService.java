package com.clubber.ClubberServer.domain.common.service;

import com.clubber.ClubberServer.global.exception.BaseErrorCode;
import com.clubber.ClubberServer.global.exception.ErrorReason;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.reflections.Reflections;
import org.springframework.stereotype.Service;

@Service
public class ExceptionService {

    public List<String> getErrorReasons() {
        Set<Class<? extends BaseErrorCode>> errorCodes =
                new Reflections("com.clubber").getSubTypesOf(BaseErrorCode.class);
        return errorCodes.stream()
                .flatMap(enumClass -> {
                    BaseErrorCode[] enumConstants = enumClass.getEnumConstants();
                    return Arrays.stream(enumConstants)
                            .map(BaseErrorCode::getErrorReason)
                            .map(ErrorReason::getReason);
                })
                .collect(Collectors.toList());
    }
}
