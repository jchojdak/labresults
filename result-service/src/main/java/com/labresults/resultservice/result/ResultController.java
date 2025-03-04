package com.labresults.resultservice.result;

import com.labresults.resultservice.result.dto.ResultDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/result")
@RequiredArgsConstructor
public class ResultController {
    private final ResultService resultService;

    @GetMapping("/test")
    public String test() {
        return "OK: RESULT-SERVICE";
    }

    @GetMapping("/{orderId}/collect")
    public List<ResultDTO> collectResultsByOrderIdAndPesel(
            @PathVariable(required = true) UUID orderId,
            @RequestParam(required = true) String pesel) {
        return resultService.getResultsByOrderIdAndPesel(orderId, pesel);
    }

}
