/*
 * Copyright 2014 James Pether Sörling
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *	$Id$
 *  $HeadURL$
*/
package com.hack23.cia.web.impl.ui.application;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

import org.eclipse.jetty.servlet.DefaultServlet;
/**
 * The Class ResourceServlet.
 */
@WebServlet(urlPatterns = { "/favicon.ico", "/robots.txt", "/sitemap.xml", "*.cache.js",
		"*.woff", "*.css" }, loadOnStartup = 1, initParams = {
                @WebInitParam(name = "gzip", value = "true"),
                @WebInitParam(name = "etags", value = "false"),
                @WebInitParam(name = "cacheControl", value = "no-cache, no-store, must-revalidate, private"),
                @WebInitParam(name = "precompressed", value = "true")
        }, asyncSupported = true)
public class ResourceServlet extends DefaultServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;


}
