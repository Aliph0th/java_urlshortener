package com.bsujava.servlet.tags;

import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class AvatarTag extends SimpleTagSupport {
    private String src;
    private String alt;
    private String className;

    public void setSrc(String src) {
        this.src = src;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public void doTag() throws JspException, IOException {
        JspWriter out = getJspContext().getOut();
        
        try {
            StringBuilder html = new StringBuilder("<img");
            
            if (src != null && !src.trim().isEmpty()) {
                html.append(" src=\"").append(src).append("\"");
            }
            
            if (alt != null && !alt.trim().isEmpty()) {
                html.append(" alt=\"").append(alt).append("\"");
            }
            
            if (className != null && !className.trim().isEmpty()) {
                html.append(" class=\"").append(className).append("\"");
            }
            
            html.append(" />");
            
            out.println(html.toString());
        } catch (IOException e) {
            throw new JspException("Error in AvatarTag: " + e.getMessage());
        }
    }
} 
