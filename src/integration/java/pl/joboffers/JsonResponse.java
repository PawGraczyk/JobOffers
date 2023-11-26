package pl.joboffers;

import org.springframework.stereotype.Component;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.util.JSONPObject;

@Component
public class JsonResponse implements SampleHttpResponse {
    @Override
    public String emptyResponse() {
        return "[]".trim();
    }

    @Override
    public String responseWithOneObject() {
        return """
                {"title":"Junior Java Developer","company":"BlueSoft Sp. z o.o.","salary":"7 000 – 9 000 PLN","offerUrl":"https://nofluffjobs.com/pl/job/junior-java-developer-bluesoft-remote-hfuanrre"}""".trim();
    }

    @Override
    public String responseWithTwoObjects() {
        return """
                [{"title":"Junior Java Developer","company":"BlueSoft Sp. z o.o.","salary":"7 000 – 9 000 PLN","offerUrl":"https://nofluffjobs.com/pl/job/junior-java-developer-bluesoft-remote-hfuanrre"},
                {"title":"Java (CMS) Developer","company":"Efigence SA","salary":"16 000 – 18 000 PLN","offerUrl":"https://nofluffjobs.com/pl/job/java-cms-developer-efigence-warszawa-b4qs8loh"}]""".trim();
    }

    @Override
    public String responseWithFourObjects() {
        return """
                [{"title":"Junior Java Developer","company":"BlueSoft Sp. z o.o.","salary":"7 000 – 9 000 PLN","offerUrl":"https://nofluffjobs.com/pl/job/junior-java-developer-bluesoft-remote-hfuanrre"},
                {"title":"Java (CMS) Developer","company":"Efigence SA","salary":"16 000 – 18 000 PLN","offerUrl":"https://nofluffjobs.com/pl/job/java-cms-developer-efigence-warszawa-b4qs8loh"},
                {"title":"Junior Java Developer","company":"Sollers Consulting","salary":"7 500 – 11 500 PLN","offerUrl":"https://nofluffjobs.com/pl/job/junior-java-developer-sollers-consulting-warszawa-s6et1ucc"},
                {"title":"Junior Java Backend Developer","company":"Enigma SOI","salary":"6 300 – 12 000 PLN","offerUrl":"https://nofluffjobs.com/pl/job/junior-java-backend-developer-enigma-soi-warszawa-ziaekkrf"}])
                """.trim();
    }

}
