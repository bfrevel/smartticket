import { v4 as uuid } from "uuid";

export const setupClientId = () => {
    const oldClientId = getClientId();

    if (oldClientId != null) {
        return oldClientId;
    } else {
        const newClientId = uuid();
        document.cookie = `clientId=${newClientId}`;
        return newClientId;
    }
};

export const getClientId = () => {
    if (document.cookie == "")
        return null;
    const clientIdString = document.cookie.split("; ")
        .find((item) => item.split("=")[0] == 'clientId');

    return clientIdString === null ? null : clientIdString.split("=")[1];

};