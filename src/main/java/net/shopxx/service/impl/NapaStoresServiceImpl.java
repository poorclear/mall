package net.shopxx.service.impl;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.shopxx.dao.NapaStoresDao;
import net.shopxx.entity.Member;
import net.shopxx.entity.NapaStores;
import net.shopxx.service.NapaStoresService;
/**
 * Service - 会员关联的店铺
 * 
 * @author SHOP++ Team
 * @version 5.0.3
 */
@Service
public class NapaStoresServiceImpl extends BaseServiceImpl<NapaStores, Long> implements NapaStoresService{
	@Inject
	private NapaStoresDao napaStoresDao;
	
	@Transactional(readOnly = true)
	public NapaStores findByNapaCode(String napaCode) {
		return napaStoresDao.find("napaCode", napaCode);
	}
	
	@Transactional(readOnly = true)
	public NapaStores findByMember(Member member){
		return napaStoresDao.find("member", member);
	}
}
