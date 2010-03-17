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
package org.jivesoftware.smackx.pubsub;

import org.jivesoftware.smackx.pubsub.LeafNode;
import org.jivesoftware.smack.packet.IQ.Type;
import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.pubsub.packet.PubSub;
import org.jivesoftware.smackx.pubsub.oauth.OAuthSubscribeExtension;

import org.jivesoftware.smackx.pubsub.oauth.OAuthParameters;

/**
* Represents a leaf node that is signed with OAuth.
* @author Chris Moos
*/
public class OAuthLeafNode extends LeafNode {
  private OAuthParameters params;
    
  public OAuthLeafNode(Connection connection, String nodeName) {
    super(connection, nodeName);
  }
  
  public void setOAuthParameters(OAuthParameters params) {
    this.params = params;
  }

  /**
  * Subscribes to a node using the supplied jid. 
  * Signs the request with OAuth.
  */
  public Subscription subscribe(String jid)
		throws XMPPException
	{
	  PubSub reply = (PubSub)sendPubsubPacket(Type.SET, new OAuthSubscribeExtension(jid, getId(), params));
		return (Subscription)reply.getExtension(PubSubElementType.SUBSCRIPTION);
  }
}