package edu.andrewisnew.java.spring.mvc;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UrlPathHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/home")
public class HomeController {
    // matches http://localhost:8080/mvc-basic/home/today
    @RequestMapping(value = "/today") //method не установлен, будет обрабатывать каждый
    public String today(Model model) {
        LocalDateTime today = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM uuuu");
        model.addAttribute("today", today.format(formatter));
        return "home/today"; //с этой строкой ассоциирован какой-то view. View будет заполнен данными из Model
    }

    // matches http://localhost:8080/mvc-basic/home/hello?name=Bub
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello(@RequestParam("name") String name, Model model) {
        model.addAttribute("name", name);
        return "home/hello";
    }

    @GetMapping(value = "/hello2")
    public String hello2(@RequestParam("name") String name, Model model) {
        model.addAttribute("name", name);
        return "home/hello";
    }

    //http://localhost:8080/mvc-basic/home/showPerson?personId=105
    @GetMapping(value = "/showPerson1")
    public String showPerson1(@RequestParam("personId") Long id, Model model) {//Если personId не указан, то будет использовано имя параметра
        return "stub";
    }

    //http://localhost:8080/mvc-basic/home/105
    @GetMapping(value = "/{personId}")
    public String showPerson2(@PathVariable("personId") Long id, Model model) {//Если personId не указан, то будет использовано имя параметра
        return "stub";
    }

    @GetMapping("/headers")
    public String headers(@RequestHeader(value = "Host") String host, @RequestHeader(value = "User-Agent") String userAgent, Model model) {
        List<String> dataList = new ArrayList<>();
        dataList.add("These are the headers for this request");
        dataList.add("Host: " .concat(host));
        dataList.add("User-Agent:" .concat(userAgent));
        model.addAttribute("dataList", dataList);
        return "home/sandbox";
    }

    @GetMapping("/request")
    public String webRequest(WebRequest webRequest, Model model) {
        List<String> dataList = new ArrayList<>();
        dataList.add("These are the details of this request: ");
        dataList.add(webRequest.getContextPath());
        webRequest.getHeaderNames().forEachRemaining(dataList::add);
        dataList.add(webRequest.getDescription(true));
        model.addAttribute("dataList", dataList);
        return "home/sandbox";
    }

    @GetMapping("/response")
    public String webResponse(HttpServletResponse response, Model model) {
        List<String> dataList = new ArrayList<>();
        dataList.add("Response was modified. Check the cookies.");
        model.addAttribute("dataList", dataList);
        response.addCookie(new Cookie("SANDBOX_COOKIE", "Delicious"));
        return "home/sandbox";
    }

    //Также параметром можно передать сессию HttpSession. Но тогда ожидается что она есть
    //Параметр HttpMethod, Locale

    //BindingResult instance
    //validates and stores validation results. The @Validated annotation is
    //a Spring provided equivalent of javax.validation.Valid that marks
    //an object for validation
//    @PostMapping
//    public String save(@Validated Person person, BindingResult result, Locale locale) {
//        if (result.hasErrors()) {
//            return "persons/edit";
//        }
//        if(person.getNewPassword() != null) {
//            person.setPassword(person.getNewPassword());
//        }
//        try {
//            personService.save(person);
//            return "redirect:/persons/" + person.getId();
//        } catch (Exception e ) {
//            Throwable cause = e.getCause().getCause();
//            if (cause instanceof ConstraintViolationException) {
//                processViolation( (ConstraintViolationException)
//                                cause, result,
//                        locale);
//            }
//            return "persons/edit";
//        }
//    }

    @GetMapping("/reader")
    public String readBody(Reader reader, Model model) throws //в reader row servlet request
            IOException {
        List<String> dataList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(reader)) {
            String line = null;
            while ((line = br.readLine()) != null) {
                dataList.add(line);
            }
        }
        model.addAttribute("dataList", dataList);
        return "home/sandbox";
    }

    @ResponseBody //disables view resolving
    @GetMapping("/writer")
    public void writeBody(Reader requestReader, Writer reponseWriter) throws IOException {
        try (BufferedReader br = new BufferedReader(requestReader)) {
            String line = null;
            while ((line = br.readLine()) != null) {
                reponseWriter.append(line).append("\n");
            }
        }
    }

    //URLs with matrix parameters are not
    //supported by default in Spring.
    //Extra configuration is needed: a
    //bean of type UrlPathHelper with the removeSemicolonContent
    //property set to false needs to be added to the MVC configuration (см WebConfig)
    //Handles:http://localhost:8080/mvc-basic/home/building/75;g=1;u=3
    @GetMapping("/building/{buildingId}")
    public String matrix(@PathVariable String buildingId, @MatrixVariable int g, @MatrixVariable int u, Model model) {
        List<String> dataList = new ArrayList<>();
        dataList.add("building number: " .concat(buildingId));
        dataList.add("ground floor flat number: " + g);
        dataList.add("upper floor flat number: " + u);
        model.addAttribute("dataList", dataList);
        return "home/sandbox";
    }

    //Handles:http://localhost:8080/mvc-basic/home/building/75;g=1;u=3
    @GetMapping("/building2/{buildingId}")
    public String matrix2(@PathVariable String buildingId,
                          @MatrixVariable Map<String, Integer> matrixVars,
                          Model model) {
        List<String> dataList = new ArrayList<>();
        dataList.add("building number: " .concat(buildingId));
        matrixVars.forEach((k, v) -> dataList.add("%s flat number: %s" .formatted(k, v)));
        model.addAttribute("dataList", dataList);
        return "home/sandbox";
    }

    @Configuration
    @EnableWebMvc
    @ComponentScan(basePackages = {"com.apress.cems.web.controllers"})
    class WebConfig implements WebMvcConfigurer {
        @Override
        public void configurePathMatch(PathMatchConfigurer configurer) {
            UrlPathHelper matrixPathHelper = new UrlPathHelper();
            matrixPathHelper.setRemoveSemicolonContent(false);
            configurer.setUrlPathHelper(matrixPathHelper);
        }
    }


    //@CookieValue - для куков


    @GetMapping("/verifyRedirection")
    public String redirectData(final RedirectAttributes redirectAttributes) {
        List<String> dataList = new ArrayList<>();
        dataList.add("Data from HomeController.redirectData");
        redirectAttributes.addFlashAttribute("dataList",
                dataList);
        return "redirect:/home/data-list";
    }

    /**
     * Method which is called via "redirect:"
     * from the HomeController.redirectData(..)
     */
    @GetMapping(value = "/data-list")
    public String listData(@ModelAttribute("dataList")
                           ArrayList<String> content) {
        return "home/sandbox";
    }

    //@SessionAttribute or @RequestAttribute

    //@ResponseStatus

    @RequestMapping(value = "/ba/{personId}", method = RequestMethod.GET)
    public ModelAndView show(@PathVariable("personId") Long id) {
        ModelAndView modelAndView = new ModelAndView("person");
        return modelAndView;
    }
}
