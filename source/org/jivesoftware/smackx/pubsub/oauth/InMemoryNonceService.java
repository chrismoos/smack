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

import java.util.concurrent.ConcurrentHashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

import org.jivesoftware.smack.util.StringUtils;

/**
* Provides unique nonces. They will be unique for each consumer/access token pair.
*
* @author Chris Moos
*/
public class InMemoryNonceService implements NonceService {
  private ConcurrentHashMap<String, Object> resourceTokens = new ConcurrentHashMap<String, Object>();

  public String generateNonce(OAuthToken consumerToken, OAuthToken token) {
    ConcurrentHashMap<Object, Object> tokens = (ConcurrentHashMap<Object, Object>)resourceTokens.get(consumerToken.getValue());
    String nonce = StringUtils.randomString(16);
    List nonceList;
    Object tokenID = token.getValue();
    
    /* Check to see if we have a list of OAuthToken tokens for this resource */
    if(tokens == null) {
      tokens = new ConcurrentHashMap<Object, Object>();
      resourceTokens.put(consumerToken.getValue(), tokens);
    }
    
    /* Check to see if we have a list of nonces for this OAuthConsumerToken */
    nonceList = (List)tokens.get(tokenID);
    
    /* Create a new list with nonces for this OAuthConsumerToken */
    if(nonceList == null) {
      nonceList = Collections.synchronizedList(new ArrayList());
      tokens.put(tokenID, nonceList);
    }
    
    /* Loop until we find a new nonce */
    while(true) {
      if(!nonceList.contains(nonce)) {
        break;
      }
      nonce = StringUtils.randomString(16);
    }
    
    return nonce;
  }
}