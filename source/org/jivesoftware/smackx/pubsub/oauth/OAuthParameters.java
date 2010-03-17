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

/**
* Parameters used for OAuth signing.
*
* You must supply all parameters for OAuth signing to be successful. 
*
* @author Chris Moos
*/
public class OAuthParameters {
  private OAuthToken consumerToken;
  private OAuthToken accessToken;
  
  private String stanzaName = "iq"; /* default stanza name */
  private String jid;
  private String toAddress;
  
  private NonceService nonceService;
  
  public OAuthParameters(OAuthToken consumerToken, OAuthToken accessToken, NonceService nonceService, String jid, String toAddress) {
    this.consumerToken = consumerToken;
    this.accessToken = accessToken;
    this.jid = jid;
    this.toAddress = toAddress;
    this.nonceService = nonceService;
  }
  
  public void setStanzaName(String stanzaName) {
    this.stanzaName = stanzaName;
  }
  
  public OAuthToken getConsumerToken() {
    return consumerToken;
  }
  
  public OAuthToken getAccessToken() {
    return accessToken;
  }
  
  public NonceService getNonceService() {
    return nonceService;
  }
  
  public String getStanzaName() {
    return stanzaName;
  }
  
  public String getJid() {
    return jid;
  }
  
  public String getToAddress() {
    return toAddress;
  }
}