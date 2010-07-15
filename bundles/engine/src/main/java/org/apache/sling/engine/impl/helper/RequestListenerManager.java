/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.sling.engine.impl.helper;

import org.apache.sling.api.request.SlingRequestEvent;
import org.apache.sling.api.request.SlingRequestListener;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

public class RequestListenerManager  {

    private final ServiceTracker serviceTracker;

	public RequestListenerManager( final BundleContext context ) {
		serviceTracker = new ServiceTracker( context, SlingRequestListener.SERVICE_NAME, null );
		serviceTracker.open();
	}

	public void sendEvent ( final SlingRequestEvent event ) {
		final Object[] services = serviceTracker.getServices();
		if ( services != null ) {
			for ( final Object service : services ) {
				( (SlingRequestListener) service ).onEvent( event );
			}
		}
	}

	public void dispose() {
	    this.serviceTracker.close();
	}
}
