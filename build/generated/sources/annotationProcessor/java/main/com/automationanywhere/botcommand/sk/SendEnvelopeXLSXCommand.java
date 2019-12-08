package com.automationanywhere.botcommand.sk;

import com.automationanywhere.bot.service.GlobalSessionContext;
import com.automationanywhere.botcommand.BotCommand;
import com.automationanywhere.botcommand.data.Value;
import com.automationanywhere.botcommand.exception.BotCommandException;
import com.automationanywhere.commandsdk.i18n.Messages;
import com.automationanywhere.commandsdk.i18n.MessagesFactory;
import com.automationanywhere.core.security.SecureString;
import java.lang.ClassCastException;
import java.lang.Deprecated;
import java.lang.Object;
import java.lang.String;
import java.lang.Throwable;
import java.util.Map;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class SendEnvelopeXLSXCommand implements BotCommand {
  private static final Logger logger = LogManager.getLogger(SendEnvelopeXLSXCommand.class);

  private static final Messages MESSAGES_GENERIC = MessagesFactory.getMessages("com.automationanywhere.commandsdk.generic.messages");

  @Deprecated
  public Optional<Value> execute(Map<String, Value> parameters, Map<String, Object> sessionMap) {
    return execute(null, parameters, sessionMap);
  }

  public Optional<Value> execute(GlobalSessionContext globalSessionContext,
      Map<String, Value> parameters, Map<String, Object> sessionMap) {
    logger.traceEntry(() -> parameters != null ? parameters.toString() : null, ()-> sessionMap != null ?sessionMap.toString() : null);
    SendEnvelopeXLSX command = new SendEnvelopeXLSX();
    if(parameters.get("accessToken") == null || parameters.get("accessToken").get() == null) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.validation.notEmpty","accessToken"));
    }

    if(parameters.get("accountId") == null || parameters.get("accountId").get() == null) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.validation.notEmpty","accountId"));
    }

    if(parameters.get("signerName") == null || parameters.get("signerName").get() == null) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.validation.notEmpty","signerName"));
    }

    if(parameters.get("signerEmail") == null || parameters.get("signerEmail").get() == null) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.validation.notEmpty","signerEmail"));
    }

    if(parameters.get("doc") == null || parameters.get("doc").get() == null) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.validation.notEmpty","doc"));
    }

    if(parameters.get("anchor") == null || parameters.get("anchor").get() == null) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.validation.notEmpty","anchor"));
    }

    if(parameters.get("anchorX") == null || parameters.get("anchorX").get() == null) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.validation.notEmpty","anchorX"));
    }

    if(parameters.get("anchorY") == null || parameters.get("anchorY").get() == null) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.validation.notEmpty","anchorY"));
    }

    if(parameters.get("accessToken") != null && parameters.get("accessToken").get() != null && !(parameters.get("accessToken").get() instanceof SecureString)) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","accessToken", "SecureString", parameters.get("accessToken").get().getClass().getSimpleName()));
    }
    if(parameters.get("accountId") != null && parameters.get("accountId").get() != null && !(parameters.get("accountId").get() instanceof SecureString)) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","accountId", "SecureString", parameters.get("accountId").get().getClass().getSimpleName()));
    }
    if(parameters.get("signerName") != null && parameters.get("signerName").get() != null && !(parameters.get("signerName").get() instanceof String)) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","signerName", "String", parameters.get("signerName").get().getClass().getSimpleName()));
    }
    if(parameters.get("signerEmail") != null && parameters.get("signerEmail").get() != null && !(parameters.get("signerEmail").get() instanceof String)) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","signerEmail", "String", parameters.get("signerEmail").get().getClass().getSimpleName()));
    }
    if(parameters.get("doc") != null && parameters.get("doc").get() != null && !(parameters.get("doc").get() instanceof String)) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","doc", "String", parameters.get("doc").get().getClass().getSimpleName()));
    }
    if(parameters.get("anchor") != null && parameters.get("anchor").get() != null && !(parameters.get("anchor").get() instanceof String)) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","anchor", "String", parameters.get("anchor").get().getClass().getSimpleName()));
    }
    if(parameters.get("anchorX") != null && parameters.get("anchorX").get() != null && !(parameters.get("anchorX").get() instanceof String)) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","anchorX", "String", parameters.get("anchorX").get().getClass().getSimpleName()));
    }
    if(parameters.get("anchorY") != null && parameters.get("anchorY").get() != null && !(parameters.get("anchorY").get() instanceof String)) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","anchorY", "String", parameters.get("anchorY").get().getClass().getSimpleName()));
    }
    try {
      Optional<Value> result =  Optional.ofNullable(command.action(parameters.get("accessToken") != null ? (SecureString)parameters.get("accessToken").get() : (SecureString)null ,parameters.get("accountId") != null ? (SecureString)parameters.get("accountId").get() : (SecureString)null ,parameters.get("signerName") != null ? (String)parameters.get("signerName").get() : (String)null ,parameters.get("signerEmail") != null ? (String)parameters.get("signerEmail").get() : (String)null ,parameters.get("doc") != null ? (String)parameters.get("doc").get() : (String)null ,parameters.get("anchor") != null ? (String)parameters.get("anchor").get() : (String)null ,parameters.get("anchorX") != null ? (String)parameters.get("anchorX").get() : (String)null ,parameters.get("anchorY") != null ? (String)parameters.get("anchorY").get() : (String)null ));
      logger.traceExit(result);
      return result;
    }
    catch (ClassCastException e) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.IllegalParameters","action"));
    }
    catch (BotCommandException e) {
      logger.fatal(e.getMessage(),e);
      throw e;
    }
    catch (Throwable e) {
      logger.fatal(e.getMessage(),e);
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.NotBotCommandException",e.getMessage()),e);
    }
  }
}
