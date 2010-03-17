/**
 * All rights reserved. Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jivesoftware.smackx.pubsub.oauth;

import org.jivesoftware.smackx.pubsub.NodeExtension;
import org.jivesoftware.smackx.pubsub.PubSubElementType;
import org.jivesoftware.smack.XMPPException;

/**
* Signs the node with OAuth.
* @author Chris Moos
*/
public class OAuthNodeExtension extends NodeExtension {  
  private OAuthParameters params;
  	
	public OAuthNodeExtension(PubSubElementType elem, OAuthParameters params)
	{
		super(elem, null);
		this.params = params;
	}
	
	@Override
	public String toXML()
	{
		String xml = super.toXML();
		
		try {
		  xml += OAuth.getElement(params);
		}
		catch(XMPPException e) {
		  /* unable to generate oauth element */  
		}
		
		return xml;
	}
}