async function submitPurchase() {

    var name = document.getElementById("name").value;
    var email = document.getElementById("email").value;
    var phone = document.getElementById("phone").value;
    var address = document.getElementById("address").value;
    var ccNumber = document.getElementById("ccNumber").value;

    if (name !== ''
            && email !== ''
            && phone !== ''
            && address !== ''
            && ccNumber !== '') {

        let data = {
            "name": String(name),
            "email": String(email),
            "phone": String(phone),
            "address": String(address),
            "ccNumber": String(ccNumber)
        };

        let response = await fetch('control/checkout/purchase/submit', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
            body: JSON.stringify(data)
        });

        if (response.ok) {
            let json = await response.json();

            sessionStorage.setItem('confirmationInfo', JSON.stringify(json.confirmationInfo));

            window.location.href = "/eshop/confirmation";
        } else {
            alert("Error HTTP: " + response.status);
        }
    }
};