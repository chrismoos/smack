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

import org.jivesoftware.smack.util.Base64;

import java.security.NoSuchAlgorithmException;
import java.security.InvalidKeyException;
import java.security.SignatureException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.jivesoftware.smack.XMPPException;

/**
* Creates the OAuth element and signs it. XEP-235.
*
* Currently the only supported algorithm is HMAC-SHA1.
*
* @author Chris Moos
*/
public class OAuth {
  public static final String OAUTH_VERSION = "1.0";
  public static final String OAUTH_XMLNS = "urn:xmpp:oauth:0";
  public static final String SIGNATURE_METHOD = "HMAC-SHA1";
  
  private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";
  
  public static String getElement(OAuthParameters params) 
   throws XMPPException
  {
    StringBuilder builder = new StringBuilder();
    String nonce = params.getNonceService().generateNonce(params.getConsumerToken(), params.getAccessToken());
    String timestamp = String.valueOf(System.currentTimeMillis());
    
    builder.append("<oauth xmlns='");
		builder.append(OAUTH_XMLNS);
		builder.append("'>");
		builder.append("<oauth_consumer_key>");
		builder.append(params.getConsumerToken().getValue());
		builder.append("</oauth_consumer_key>");
		builder.append("<oauth_nonce>");
		builder.append(nonce);
		builder.append("</oauth_nonce>");
		builder.append("<oauth_signature>");
		builder.append(generateSignature(params, nonce, timestamp));
		builder.append("</oauth_signature>");
		builder.append("<oauth_signature_method>");
		builder.append(SIGNATURE_METHOD);
		builder.append("</oauth_signature_method>");
		builder.append("<oauth_timestamp>");
		builder.append(timestamp);
		builder.append("</oauth_timestamp>");
		builder.append("<oauth_token>");
		builder.append(params.getAccessToken().getValue());
		builder.append("</oauth_token>");
		builder.append("<oauth_version>");
		builder.append(OAUTH_VERSION);
		builder.append("</oauth_version>");
		builder.append("</oauth>");
    
    return builder.toString();
  }
  
  private static String generateSignature(OAuthParameters params, String nonce, String timestamp) 
    throws XMPPException 
  {
    StringBuilder sb = new StringBuilder();
    String signature;
    String key = params.getConsumerToken().getSecret() + "&" + params.getAccessToken().getSecret();
    SecretKeySpec spec = new SecretKeySpec(key.getBytes(), HMAC_SHA1_ALGORITHM);
    
    try {
      Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
      
      sb.append(params.getStanzaName() + "&");
      sb.append(OAuthEncoder.encode(params.getJid() + "&" + params.getToAddress()));
      sb.append("&");
      sb.append(OAuthEncoder.encode("oauth_consumer_key=" + params.getConsumerToken().getValue() + "&"));
      sb.append(OAuthEncoder.encode("oauth_nonce=" + nonce + "&"));
      sb.append(OAuthEncoder.encode("oauth_signature_method=" + SIGNATURE_METHOD + "&"));
      sb.append(OAuthEncoder.encode("oauth_timestamp=" + timestamp + "&"));
      sb.append(OAuthEncoder.encode("oauth_token=" + params.getAccessToken().getValue() + "&"));
      sb.append(OAuthEncoder.encode("oauth_version=" + OAUTH_VERSION));

      mac.init(spec);

      byte[] result = mac.doFinal(sb.toString().getBytes());
      signature = Base64.encodeBytes(result);
    }
    catch(NoSuchAlgorithmException e) {
      throw new XMPPException("No such algorithm: " + e.getMessage());
    }
    catch(InvalidKeyException e1) {
      throw new XMPPException("Invalid key: " + e1.getMessage());
    }
    
    return signature;
  }
}