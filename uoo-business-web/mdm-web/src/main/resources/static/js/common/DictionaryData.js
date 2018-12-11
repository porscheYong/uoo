function Dictionary() {
  this._cityVillage = []; //城乡属性
  this._scale = []; //规模
  this._orgPostLevel = []; //组织岗位级别
  this._columType = []; //字段类型
  this._colNullable = []; //字段是否可以空
  this._editTable = []; //可否编辑
  this._statusCd = []; //状态
  this._yesNo = []; //是否
  this._ruleOperator = []; //规则操作符
  this._nationality = []; //国籍
  this._gender = []; //性别
  this._nation = []; //民族
  this._marriage = []; //婚姻状况
  this._pliticalStatus = []; //政治面貌
  this._schoolType = []; //学校类别
  this._memRelation = []; //与本人关系
  this._certType = []; //证件类型
  this._acctType = []; //账号类型
  this._userHostType = []; //用户主体类型
  this._orgTreeType = []; //组织树类型
  this._relaType = []; //关系类型
  this._property = []; //用工性质
  this._refType = []; //组织人员关系类型
  this._roleType = []; //角色类型
  this._grantObjType = []; //授权对象类型
  this._privType = []; //权限类型
  this._privRefType = []; //关联功能类型
  this._compType = []; //模块组件类型
  this._intfType = []; //接口类型
  this._connectType = []; //连接方式
  this._regionType = []; //区域类型
  this._locType = []; //行政区域类型
  this._postType = []; //职位类型
  this._positionType = []; //岗位类型
  this._nodeType = []; //组织节点
  this._areaType = []; //区域级别
  this._countType = []; //统计属性
  this._contractType = []; //承包类型
}

Dictionary.prototype.cityVillage = function () {
  if (arguments.length === 1) {
      this._cityVillage = arguments[0];
  }
  else {
      return this._cityVillage;
  }
}

Dictionary.prototype.scale = function () {
  if (arguments.length === 1) {
      this._scale = arguments[0];
  }
  else {
      return this._scale;
  }
}

Dictionary.prototype.orgPostLevel = function () {
  if (arguments.length === 1) {
      this._orgPostLevel = arguments[0];
  }
  else {
      return this._orgPostLevel;
  }
}

Dictionary.prototype.columType = function () {
  if (arguments.length === 1) {
      this._columType = arguments[0];
  }
  else {
      return this._columType;
  }
}

Dictionary.prototype.colNullable = function () {
  if (arguments.length === 1) {
      this._colNullable = arguments[0];
  }
  else {
      return this._colNullable;
  }
}

Dictionary.prototype.editTable = function () {
  if (arguments.length === 1) {
      this._editTable = arguments[0];
  }
  else {
      return this._editTable;
  }
}

Dictionary.prototype.statusCd = function () {
  if (arguments.length === 1) {
      this._statusCd = arguments[0];
  }
  else {
      return this._statusCd;
  }
}

Dictionary.prototype.yesNo = function () {
  if (arguments.length === 1) {
      this._yesNo = arguments[0];
  }
  else {
      return this._yesNo;
  }
}

Dictionary.prototype.ruleOperator = function () {
  if (arguments.length === 1) {
      this._ruleOperator = arguments[0];
  }
  else {
      return this._ruleOperator;
  }
}

Dictionary.prototype.nationality = function () {
  if (arguments.length === 1) {
      this._nationality = arguments[0];
  }
  else {
      return this._nationality;
  }
}

Dictionary.prototype.gender = function () {
  if (arguments.length === 1) {
      this._gender = arguments[0];
  }
  else {
      return this._gender;
  }
}

Dictionary.prototype.nation = function () {
  if (arguments.length === 1) {
      this._nation = arguments[0];
  }
  else {
      return this._nation;
  }
}

Dictionary.prototype.marriage = function () {
  if (arguments.length === 1) {
      this._marriage = arguments[0];
  }
  else {
      return this._marriage;
  }
}

Dictionary.prototype.pliticalStatus = function () {
  if (arguments.length === 1) {
      this._pliticalStatus = arguments[0];
  }
  else {
      return this._pliticalStatus;
  }
}

Dictionary.prototype.schoolType = function () {
  if (arguments.length === 1) {
      this._schoolType = arguments[0];
  }
  else {
      return this._schoolType;
  }
}

Dictionary.prototype.memRelation = function () {
  if (arguments.length === 1) {
      this._memRelation = arguments[0];
  }
  else {
      return this._memRelation;
  }
}

Dictionary.prototype.certType = function () {
  if (arguments.length === 1) {
      this._certType = arguments[0];
  }
  else {
      return this._certType;
  }
}

Dictionary.prototype.acctType = function () {
  if (arguments.length === 1) {
      this._acctType = arguments[0];
  }
  else {
      return this._acctType;
  }
}

Dictionary.prototype.userHostType = function () {
  if (arguments.length === 1) {
      this._userHostType = arguments[0];
  }
  else {
      return this._userHostType;
  }
}

Dictionary.prototype.orgTreeType = function () {
  if (arguments.length === 1) {
      this._orgTreeType = arguments[0];
  }
  else {
      return this._orgTreeType;
  }
}

Dictionary.prototype.relaType = function () {
  if (arguments.length === 1) {
      this._relaType = arguments[0];
  }
  else {
      return this._relaType;
  }
}

Dictionary.prototype.property = function () {
  if (arguments.length === 1) {
      this._property = arguments[0];
  }
  else {
      return this._property;
  }
}

Dictionary.prototype.refType = function () {
  if (arguments.length === 1) {
      this._refType = arguments[0];
  }
  else {
      return this._refType;
  }
}

Dictionary.prototype.roleType = function () {
  if (arguments.length === 1) {
      this._roleType = arguments[0];
  }
  else {
      return this._roleType;
  }
}

Dictionary.prototype.grantObjType = function () {
  if (arguments.length === 1) {
      this._grantObjType = arguments[0];
  }
  else {
      return this._grantObjType;
  }
}

Dictionary.prototype.privType = function () {
  if (arguments.length === 1) {
      this._privType = arguments[0];
  }
  else {
      return this._privType;
  }
}

Dictionary.prototype.privRefType = function () {
  if (arguments.length === 1) {
      this._privRefType = arguments[0];
  }
  else {
      return this._privRefType;
  }
}

Dictionary.prototype.compType = function () {
  if (arguments.length === 1) {
      this._compType = arguments[0];
  }
  else {
      return this._compType;
  }
}

Dictionary.prototype.intfType = function () {
  if (arguments.length === 1) {
      this._intfType = arguments[0];
  }
  else {
      return this._intfType;
  }
}

Dictionary.prototype.connectType = function () {
  if (arguments.length === 1) {
      this._connectType = arguments[0];
  }
  else {
      return this._connectType;
  }
}

Dictionary.prototype.regionType = function () {
  if (arguments.length === 1) {
      this._regionType = arguments[0];
  }
  else {
      return this._regionType;
  }
}

Dictionary.prototype.locType = function () {
  if (arguments.length === 1) {
      this._locType = arguments[0];
  }
  else {
      return this._locType;
  }
}

Dictionary.prototype.postType = function () {
  if (arguments.length === 1) {
      this._postType = arguments[0];
  }
  else {
      return this._postType;
  }
}

Dictionary.prototype.positionType = function () {
  if (arguments.length === 1) {
      this._positionType = arguments[0];
  }
  else {
      return this._positionType;
  }
}

Dictionary.prototype.nodeType = function () {
  if (arguments.length === 1) {
      this._nodeType = arguments[0];
  }
  else {
      return this._nodeType;
  }
}

Dictionary.prototype.areaType = function () {
  if (arguments.length === 1) {
      this._areaType = arguments[0];
  }
  else {
      return this._areaType;
  }
}

Dictionary.prototype.countType = function () {
  if (arguments.length === 1) {
      this._countType = arguments[0];
  }
  else {
      return this._countType;
  }
}

Dictionary.prototype.contractType = function () {
  if (arguments.length === 1) {
      this._contractType = arguments[0];
  }
  else {
      return this._contractType;
  }
}
