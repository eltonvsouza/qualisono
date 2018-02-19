package br.com.util;

//import java.util.GregorianCalendar;
import java.util.List;

import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;

import br.com.jsms.core.carrier.CarrierDescriptionImpl;
import br.com.jsms.core.carrier.CarrierImpl;
import br.com.jsms.core.carrier.CarrierSupported;
import br.com.jsms.core.carrier.helper.StageParametersBuilder;
import br.com.jsms.core.carrier.stage.GetCarrierStage;
import br.com.jsms.core.carrier.stage.PostCarrierStage;
import br.com.jsms.core.carrier.stage.RetrieveCarrierStage;
import br.com.jsms.domain.carrier.CarrierDescription;
import br.com.jsms.domain.carrier.CarrierRuntimeData;
import br.com.jsms.domain.carrier.CarrierStage;
import br.com.jsms.domain.carrier.PortabilityCarrier;
import br.com.jsms.domain.message.SmsMessage;
import br.com.jsms.domain.message.SmsStatus;
import br.com.jsms.exception.JSMSException;
import br.com.jsms.helper.StringHelper;
import br.com.jsms.helper.UserContactFormatter;

//@CarrierSupported
public class ClearTechCarrier{
//public class ClearTechCarrier extends CarrierImpl implements PortabilityCarrier {
//
//	private static final String JCID = "jcid";
//
//	public ClearTechCarrier() {
//		super();
//
//		setHost(new HttpHost("consultanumero.abr.net.br", 8080, "http"));
//	}
//
//	@Override
//	public SmsStatus checkForStatus(CarrierRuntimeData runtimeData, SmsMessage sms, String lastPage) {
//		if (lastPage.contains("Digite os caracteres corretamente")) {
//			return SmsStatus.InvalidCode;
//		}
//
//		if (lastPage.contains("Não existem dados retornados para a consulta")) {
//			return SmsStatus.UnsupportedPhoneNumber;
//		}
//
//		String lastString = lastPage;
//		try {
//			String carrierName = StringHelper.substring(lastPage, "<tr class=\"gridselecionado\">", false, "</tr>");
//			lastString = carrierName;
//
//			carrierName = carrierName.substring(5);
//			lastString = carrierName;
//
//			carrierName = carrierName.substring(carrierName.indexOf('>') + 1);
//			lastString = carrierName;
//
//			carrierName = StringHelper.substring(carrierName, ">", false, "<");
//			lastString = carrierName;
//
//			if (carrierName.length() == 0) {
//				throw new StringIndexOutOfBoundsException(-1);
//			}
//
//			carrierName = StringHelper.removeSpecialCharacters(carrierName).toUpperCase();
//			System.out.println(carrierName);
//			runtimeData.setValue(CarrierRuntimeData.RuntimeKey_CarrierName, carrierName);
//
//			return SmsStatus.Ok;
//		} catch (StringIndexOutOfBoundsException e) {
//			System.err.println(lastPage);
//			System.err.println();
//			System.err.println(lastString);
//			return SmsStatus.Error;
//		}
//	}
//
//	@Override
//	protected CarrierDescription createDescription() {
//		CarrierDescriptionImpl description = new CarrierDescriptionImpl();
//		// description.setImageIcon();
//		description.setLastUpdate(new GregorianCalendar(2009, 2, 6));
//		description.setMessageLimit(121);
//		description.setName("ClearTech");
//		description.setVersion("1");
//		// description.setSmallIcon();
//		return description;
//	}
//
//	@Override
//	protected void defineStages() {
//		CarrierStage stage1 = new GetCarrierStage("/consultanumero/consulta/consultaSituacaoAtual.action");
//		addStage(stage1);
//
//		CarrierStage stage2 = new RetrieveCarrierStage() {
//
//			@Override
//			public String[] getImagesFromPage(CarrierRuntimeData runtimeData, String page) throws JSMSException {
//				String imageUrl = StringHelper.substring(page, "/consultanumero/jcaptcha.jpg", true, "'");
//
//				String jcid = StringHelper.substring(imageUrl, "jcid=", false, null);
//				runtimeData.setValue(JCID, jcid);
//
//				return new String[] {imageUrl};
//			}
//
//		};
//		addStage(stage2);
//
//		CarrierStage stage3 = new PostCarrierStage("/consultanumero/consulta/consultaSituacaoAtual.action") {
//
//			@Override
//			public List<NameValuePair> getParameters(CarrierRuntimeData runtimeData, SmsMessage sms, String code) {
//
//				return StageParametersBuilder.create().
//						add("j_captcha_response", code).
//						add("jcid", runtimeData.getValue(JCID)).
//						add("method:consultar", "Consultar").
//						add("nmTelefone", UserContactFormatter.getFormattedNumber(sms.getReceiver())).
//						build();
//			}
//
//		};
//		addStage(stage3);
//	}

}