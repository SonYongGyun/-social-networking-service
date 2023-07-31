package kr.co.mz.sns.mapper;

import java.util.function.Function;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ModelMapperService {

    private final ModelMapper modelMapper;

    public <FIRST, SECOND, THIRD> Stream<THIRD> mapAndActAndMap(Stream<FIRST> stream, Class<SECOND> secondType,
        Function<SECOND, SECOND> function, Class<THIRD> thirdType) {
        return stream
            .map(first -> modelMapper.map(first, secondType))
            .map(function)
            .map(second -> modelMapper.map(second, thirdType));
    }
}
