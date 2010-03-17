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

import org.jivesoftware.smackx.pubsub.SubscribeExtension;
import org.jivesoftware.smack.XMPPException;

/**
* Subscribes with PubSub and signs it with OAuth.
* @author Chris Moos
*/
public class OAuthSubscribeExtension extends SubscribeExtension {  
  private OAuthParameters params;
  	
	public OAuthSubscribeExtension(String subscribeJid, String nodeId, OAuthParameters params)
	{
		super(subscribeJid, nodeId);
		this.params = params;
	}
	
	@Override
	public String toXML()
	{
		StringBuilder builder = new StringBuilder("<");
		builder.append(getElementName());
		
		if (getNode() != null)
		{
			builder.append(" node='");
			builder.append(getNode());
			builder.append("'");
		}
		builder.append(" jid='");
		builder.append(getJid());
		builder.append("'/>");
		
		try {
		  builder.append(OAuth.getElement(params));
		}
		catch(XMPPException e) {
		  /* unable to generate oauth element */  
		}
		
		return builder.toString();
	}
}