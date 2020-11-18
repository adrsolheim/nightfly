package no.adrsolheim.nightfly;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Random;


@RestController
public class RestEndpoints {

    @GetMapping("/")
    public String index() {
        return "Best site EU. So true..";
    }

    @GetMapping("/dice")
    public int dice() {
        return new Random().nextInt(6) + 1;
    }

}
