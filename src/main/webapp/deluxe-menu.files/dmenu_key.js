//***********************************************
//  Javascript Menu (c) 2006 - 2007, by Deluxe-Menu.com
//  Trial Version
//
//  version 2.4
//  E-mail:  cs@deluxe-menu.com
//***********************************************

//***********************************************
// Obfuscated by Javascript Obfuscator
// http://javascript-source.com
//***********************************************

function _dmwe(d){with(_dvK){if(d>0){if(iv.ii+d<ce.i.length)iv=ce.i[iv.ii+d];else iv=ce.i[0];}else{if(iv.ii+d>=0)iv=ce.i[iv.ii+d];else iv=ce.i[ce.i.length-1];};if(iv.text=='-'||iv.dss||iv.ded||!iv.qiv)_dmwe(d);if(ce==rsv)uv=iv;}}function _dmlp(d){with(_dvK)if(d>0){if(iv.dcd){_dmzh(iv.dcd);if(dm.saveNavigation)_dmhq(1);ce=_dmvi(iv.dcd);iv=ce.i[0];}else {_dmsm(dm.ii);iv=uv;ce=rsv;_dmwe(+1);}}else {if(iv==uv)return;_dmmh(ce.id);iv=_dmvi(ce.qri);ce=dm.m[iv.ci];}}function _dmhq(ov){with(_dvK){if(!iv.dpr)_dmh(iv,ov);if(!_dvD)return;var doi=_dmoi(iv.id+'tbl');if(!doi)return;var di=_dmos(doi);if(di[2]>2&&di[3]>2)with(_dvD.style){left=di[0]+1+'px';top=di[1]+1+'px';width=di[2]-2+'px';height=di[3]-2+'px';display='';}}}function _dmdk(mi,dsh){if(dsh)_dmsm(mi);with(_dvK){_dmhq(0);if(_dvD)_dvD.style.display='none';_dvD=null;qie=0;}}function _dmfi(){with(_dvK){if(dm_focus)_dvD=_dmge('dmFDIV'+_dvI);for(var i=0;i<_dm.ln();i++)_dmsm(i);qie=1;dm=_dm[_dvI];rsv=dm.m[0];uv=rsv.i[0];ce=rsv;iv=uv;_dmhq(1);}}function _dmcc(dd){if(_o&&_v<8)switch(dd){case 57346:return 113;break;case 57354:return 121;break;case 57375:return 37;break;case 57373:return 38;break;case 57376:return 39;break;case 57374:return 40;break;}return dd;}var _dvI=0;var _dvD=null;function dm_ext_keystrokes(e,win){if(_e)e=win?win.event:event;var k=_dmcc(e.keyCode);if(_dvK.qie){if(k==27){_dmdk(_dvK.dm.ii,1);return false;}if(e.ctrlKey&&k==dm_actKey&&_dm.ln()>1){_dmdk(_dvK.dm.ii,1);var _old=_dvI;do{_dvI++;if(_dvI==_dm.ln())_dvI=0;}while(_dm[_dvI].dpp&&_dvI!=_old);_dmfi();return false;}}with(_dvK)if(!qie){if(e.ctrlKey&&k==dm_actKey)_dmfi();else return true;}else {_dmhq(0);var f=0;if(ce.dhz)switch(k){case 39:_dmwe(+1);f=1;break;case 37:_dmwe(-1);f=1;break;case 38:f=1;break;case 40:_dmlp(+1);f=1;break;}else switch(k){case 39:_dmlp(+1);f=1;break;case 37:_dmlp(-1);f=1;break;case 38:_dmwe(-1);f=1;break;case 40:_dmwe(+1);f=1;break;}clearInterval(dm._dmnl);dm._dmnl=null;if(k==13&&!iv.dss){if(dm.qtm!=-2)dm_ext_setPressedItem(dm.ii,iv.ci,iv.ii,true);I1Ila(dm,iv);_dmdk(_dvK.dm.ii,1);return false;}_dmhq(1);return(f?false:true);};return false;}
