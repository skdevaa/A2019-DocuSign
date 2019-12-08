/*
 * Copyright (c) 2019 Automation Anywhere.
 * All rights reserved.
 *
 * This software is the proprietary information of Automation Anywhere.
 * You shall use it only in accordance with the terms of the license agreement
 * you entered into with Automation Anywhere.
 */
/**
 *
 */
package com.automationanywhere.botcommand.sk;

import com.automationanywhere.botcommand.data.impl.StringValue;
import com.automationanywhere.commandsdk.annotations.BotCommand;
import com.automationanywhere.commandsdk.annotations.CommandPkg;
import com.automationanywhere.commandsdk.annotations.Execute;
import com.automationanywhere.commandsdk.annotations.Idx;
import com.automationanywhere.commandsdk.annotations.Pkg;
import com.automationanywhere.commandsdk.annotations.rules.NotEmpty;
import com.automationanywhere.commandsdk.i18n.Messages;
import com.automationanywhere.commandsdk.i18n.MessagesFactory;
import com.automationanywhere.commandsdk.model.AttributeType;
import com.automationanywhere.commandsdk.model.DataType;
import com.automationanywhere.core.security.SecureString;
import com.docusign.esign.api.EnvelopesApi;
import com.docusign.esign.client.ApiClient;
import com.docusign.esign.model.Document;
import com.docusign.esign.model.EnvelopeDefinition;
import com.docusign.esign.model.EnvelopeSummary;
import com.docusign.esign.model.Recipients;
import com.docusign.esign.model.SignHere;
import com.docusign.esign.model.Signer;
import com.docusign.esign.model.Tabs;
import com.sun.jersey.core.util.Base64;

import static com.automationanywhere.commandsdk.model.AttributeType.CREDENTIAL;
import static com.automationanywhere.commandsdk.model.AttributeType.TEXT;
import static com.automationanywhere.commandsdk.model.DataType.STRING;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

/**
 *
 *
 * @author Stefan Karsten
 */

@BotCommand
@CommandPkg(return_label = "envelope ID", node_label = "sendEnvelopePDF", 
label = "Send Envelope PDF", description = "sends signing request via email for a PDF document", 
name = "sendenvelopePDF", icon = "pkg.svg", return_type = STRING, return_required = true)
public class SendEnvelopePDF{
	

	@Execute
	   public StringValue action(@Idx(index = "1", type = CREDENTIAL)  @Pkg(label = "Token" , default_value_type =  DataType.STRING) @NotEmpty SecureString accessToken,
               @Idx(index = "2", type = CREDENTIAL) @Pkg(label = "Account ID"  , default_value_type = DataType.STRING ) @NotEmpty SecureString accountId,
               @Idx(index = "3", type = TEXT) @Pkg(label = "Full Name" , default_value_type =  DataType.STRING) @NotEmpty String signerName,
               @Idx(index = "4", type = TEXT) @Pkg(label = "eMail" , default_value_type = DataType.STRING) @NotEmpty String signerEmail,
				@Idx(index = "5", type = AttributeType.FILE) @Pkg(label = "PDF file" , default_value_type = DataType.FILE) @NotEmpty String doc ,
				@Idx(index = "6", type = TEXT) @Pkg(label = "Anchor" , default_value_type = DataType.STRING) @NotEmpty String anchor,
				@Idx(index = "7", type = TEXT) @Pkg(label = "Offset X" , default_value_type = DataType.STRING) @NotEmpty String anchorX,
				@Idx(index = "8", type = TEXT) @Pkg(label = "Offset Y" , default_value_type = DataType.STRING) @NotEmpty String anchorY) throws Exception 
{

       String basePath = "https://demo.docusign.net/restapi";


       byte[] buffer;
       String docBase64 = null;
		try {
			buffer = readFile(doc);
	         docBase64 = new String(Base64.encode(buffer));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

       // Create the DocuSign document object
       Document document = new Document();
       document.setDocumentBase64(docBase64);
       document.setName("Sign Document"); // can be different from actual file name
       document.setFileExtension("pdf"); // many different document types are accepted
       document.setDocumentId("1"); // a label used to reference the doc

       // The signer object
       // Create a signer recipient to sign the document, identified by name and email
       Signer signer = new Signer();
       signer.setEmail(signerEmail);
       signer.setName(signerName);
       signer.recipientId("1");
       // Create a signHere tabs (also known as a field) on the document,
       // We're using x/y positioning. Anchor string positioning can also be used
       SignHere signHere = new SignHere();
       signHere.setDocumentId("1");
       signHere.setPageNumber("1");
       signHere.setRecipientId("1");
       signHere.anchorString(anchor);
       signHere.anchorXOffset(anchorX);
       signHere.anchorYOffset(anchorY);
       signHere.getAnchorIgnoreIfNotPresent();

       // Add the tabs to the signer object
       // The Tabs object wants arrays of the different field/tab types
       Tabs signerTabs = new Tabs();
       signerTabs.setSignHereTabs(Arrays.asList(signHere));
       signer.setTabs(signerTabs);


       // Next, create the top level envelope definition and populate it.
       EnvelopeDefinition envelopeDefinition = new EnvelopeDefinition();
       envelopeDefinition.setEmailSubject("Please sign this document");
       envelopeDefinition.setDocuments(Arrays.asList(document));
       // Add the recipient to the envelope object
       Recipients recipients = new Recipients();
       recipients.setSigners(Arrays.asList(signer));
       envelopeDefinition.setRecipients(recipients);
       envelopeDefinition.setStatus("sent"); // requests that the envelope be created and sent.

       // Step 2. Call DocuSign to create and send the envelope
       ApiClient apiClient = new ApiClient(basePath);
       apiClient.addDefaultHeader("Authorization", "Bearer " + accessToken.getInsecureString());
       EnvelopesApi envelopesApi = new EnvelopesApi(apiClient);
       EnvelopeSummary results = null;
       String envelopeId ;
	   results = envelopesApi.createEnvelope(accountId.getInsecureString(), envelopeDefinition);
	   envelopeId = results.getEnvelopeId();


       // Show results
       return  new StringValue(envelopeId);

   }
	
    // Read a file
    private byte[] readFile(String path) throws IOException {
        InputStream is = new FileInputStream(path);
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[1024];
        while ((nRead = is.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        buffer.flush();
        is.close();
        return buffer.toByteArray();
    }
	
}
