package kr.pe.withwind.nims;

import java.util.HashMap;

import kr.pe.withwind.nims.domain.ProductInfo;
import kr.pe.withwind.utils.DerbyManager;

public class ProductManager {
	
	
	private static ProductManager pManager;
	
	private HashMap<String, ProductInfo> pInfoMap;
	
	private ProductManager() {
		pInfoMap = new HashMap<String, ProductInfo>();
	}
	
	public static ProductManager getInstance() {
		if (pManager == null) pManager = new ProductManager();
		
		return pManager;
	}
	
	public ProductInfo getProductInfo(String productCd) throws NimsException {
		if (!pInfoMap.containsKey(productCd)) {
			try {
				ProductInfo pInfo = new ProductInfo();
				pInfo.setProductCd(productCd);
				pInfo = DerbyManager.getInstance().listOne("ProductInfo.selectByParam", pInfo);
				if (pInfo == null) throw new NimsException("약품정보가 존재하지 않습니다.["+productCd+"]");
				
				pInfoMap.put(productCd, pInfo);
			}catch(NimsException e) {
				throw e;
			}catch(Exception e) {
				throw new NimsException("약품정보 조회중 오류가 발생했습니다.", e);
			}
			
		}
		
		return pInfoMap.get(productCd);
	}
}
