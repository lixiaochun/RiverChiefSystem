package filemanage.service.impl;

import assessment.service.RegionService;
import filemanage.service.RegionCut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegionCutImpl implements RegionCut {

    @Autowired
    private RegionService regionService;

    @Override
    public String cut(String region) throws Exception {
        String regionid = null;
        int level;
		try {
			level = regionService.selectByPrimeryKey(region).getLevel();
		} catch (Exception e) {
			throw new Exception("区域ID错误！");
		}
        switch (level) {
            case 1:
                regionid = region.substring(0, 3);
                break;
            case 2:
                regionid = region.substring(0, 4);
                break;
            case 3:
                regionid = region.substring(0, 6);
                break;
            case 4:
                regionid = region.substring(0, 9);
                break;
            default:
                regionid = region;
        }
        return regionid;
    }

}
