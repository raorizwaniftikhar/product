package com.java.spring.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Component
public class RequestResponseFilter extends OncePerRequestFilter {
	ResponseWrapper responseWrapper = null;
	RequestWrapper requestWrapped = null;

	@SuppressWarnings("deprecation")
	@Override
	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// So that other request method behave just like before
		if (request.getMethod().toLowerCase().equalsIgnoreCase("post")
				|| request.getMethod().toLowerCase().equalsIgnoreCase("put")
				|| (request.getMethod().toLowerCase().equalsIgnoreCase("get")
						&& request.getRequestURI().contains("/api/product"))) {

			try {
				requestWrapped = new RequestWrapper(request);
				responseWrapper = new ResponseWrapper(response);
				chain.doFilter(requestWrapped, responseWrapper);
				String content = responseWrapper.getCaptureAsString();
				ObjectMapper objectMapper = new ObjectMapper();
				ObjectNode rootNode = null;
				JsonNode jsonNode = null;
				if (response.getContentType() != null && response.getContentType().contains("application/json")) {
					ArrayNode jsonArrayNode = null;
					if (content.startsWith("[")) {
						jsonArrayNode = (ArrayNode) objectMapper.readTree(content);
						rootNode = objectMapper.createObjectNode();
						rootNode.put("data", jsonArrayNode);
					} else if (content.startsWith("{")) {
						jsonNode = objectMapper.readTree(content);
						if (jsonNode.has("content") && jsonNode.get("content").isArray()
								&& !jsonNode.get("content").isNull()) {
							jsonArrayNode = (ArrayNode) objectMapper.readTree(jsonNode.get("content").toString());
							rootNode = objectMapper.createObjectNode();
							rootNode.put("data", jsonArrayNode);
							// Pagination Control
							ObjectNode pageNode = (ObjectNode) jsonNode.get("pageable");
							pageNode.put("current", pageNode.get("pageNumber"));
							pageNode.put("total", pageNode.get("pageSize"));
							pageNode.remove("pageNumber");
							pageNode.remove("pageSize");

							// overall pagination result
							ObjectNode resultNode = objectMapper.createObjectNode();
							resultNode.put("totalPages", jsonNode.get("totalPages"));
							resultNode.put("total", jsonNode.get("totalElements"));

							// page and result both a add into meta Object
							ObjectNode metaNode = objectMapper.createObjectNode();
							metaNode.put("page", pageNode);
							metaNode.put("results", resultNode);

							rootNode.put("meta", metaNode);
						} else {
							rootNode = objectMapper.createObjectNode();
							rootNode.put("data", jsonNode);
						}
					}
					// This code works fine
					response.getWriter().write(rootNode.toPrettyString());

				} else if (response.getContentType() != null
						&& response.getContentType().contains("application/problem+json")) {
					content = responseWrapper.getCaptureAsString();
					jsonNode = objectMapper.readTree(content);
					rootNode = objectMapper.createObjectNode();
					rootNode = (ObjectNode) jsonNode;
					rootNode.remove("cause");
					rootNode.remove("stackTrace");
					rootNode.remove("localizedMessage");
					rootNode.remove("suppressed");
					rootNode.remove("instance");
					if (rootNode.get("detail").isNull()) {
						rootNode.remove("detail");
					}
					if (rootNode.get("title").toString().equals(rootNode.get("message").toString())) {
						rootNode.remove("message");
					}
					// This code works fine
					response.getWriter().write(rootNode.toPrettyString());
				} else {
					response.getWriter().write(content);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else {
			chain.doFilter(request, response);
		}
	}

	@Override
	public void destroy() {

	}

}
