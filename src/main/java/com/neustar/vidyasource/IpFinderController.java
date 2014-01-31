package com.neustar.vidyasource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * Spring MVC controller that is HTTP-aware.
 */
@Controller
@RequestMapping("/")
public class IpFinderController {
    @Autowired
    private IpFinder ipFinder;

    /**
     * Derives IP from request, delegates its processing, and populates map for rendering results in the specified view.
     * @param model Spring MVC abstraction for sharing data
     * @param request Abstraction for request
     * @return Logical name of the view
     */
    @RequestMapping(method = RequestMethod.GET)
    public String location(ModelMap model, HttpServletRequest request) {
        String ip = ipFrom(request);
        model.addAttribute("message", "Neustar IP Intelligence GeoPoint On-Demand (GPP) Demo Application");
        model.addAttribute("ip", ip);
        model.addAttribute("locationMap", ipFinder.findLocationBy(ip));

        return "index";
    }

    /**
     * Derives the IP from the request.
     * @param request Abstraction for request
     * @return IP address of request
     */
    private String ipFrom(HttpServletRequest request) {
        String ip = request.getHeader("X-FORWARDED-FOR");  //In case user is behind proxy server or load balancer
        if (ip == null) {
            ip = request.getRemoteAddr();
        }

        return ip;
    }
}