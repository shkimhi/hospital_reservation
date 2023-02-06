
    const name = "${ho.userName}";
    const nameLength = name.length;
    let maskedName = '';

    if (nameLength === 2) {
    maskedName = name[0] + '*';
} else if (nameLength > 2) {
    let maskedChars = '';
    for (let i = 0; i < nameLength - 2; i++) {
    maskedChars += '*';
}
    maskedName = name[0] + maskedChars + name[nameLength - 1];
} else {
    maskedName = name;
}
    document.getElementById('userNme_re').value = maskedName;
