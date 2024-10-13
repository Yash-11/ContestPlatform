import axios from 'axios';

export const getRankingsForContest = async (contestId) => {
    const token = localStorage.getItem('token');
    const headers = {
        "Authorization": `Bearer ${token}`
    };
    try {
        const response = await axios.get(`${process.env.REACT_APP_API_BASE_URL}/api/contests/${contestId}/ranking`, { headers });
        console.log(response.data);

        return response.data;
    } catch (error) {
        console.error('Error fetching rankings:', error);
        return [];
    }
};
