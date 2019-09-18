<?
header("Content-type: text/json");
	if (((isset($_GET['user']))? strtolower($_GET['user']) : strtolower($_POST['user'])) == 'admin'
	&& ((isset($_GET['pass']))? strtolower($_GET['pass']) : strtolower($_POST['pass'])) == '1234') {
		$arrData["status"] = 1;
		$arrData["massage"] = "เข้าสู่ระบบสำเร็จ";
		$arrData["member_id"] = "1";
		$arrData["member_name"] = "ฟหกด่าสวฟหกด่าสว";
		$arrData["member_level"] = rand(1,10);
	}else {
		$arrData["status"] = 2;
		$arrData["massage"] = "ไม่พบชื่อผู้ใช้งาน";
	}
	echo json_encode($arrData);
?>
